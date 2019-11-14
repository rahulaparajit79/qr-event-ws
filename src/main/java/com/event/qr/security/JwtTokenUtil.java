package com.event.qr.security;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.event.qr.util.TimeProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;




@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -3301605591108950415L;

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_AUDIENCE = "aud";
	static final String CLAIM_KEY_CREATED = "iat";
	static final String CLAIM_KEY_LAST_PASS_DT = "pswDt";

	static final String AUDIENCE_UNKNOWN = "unknown";
	static final String AUDIENCE_WEB = "web";
	static final String AUDIENCE_MOBILE = "mobile";
	static final String AUDIENCE_TABLET = "tablet";

	@Autowired
	private TimeProvider timeProvider;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public String getAudienceFromToken(String token) {
		return getClaimFromToken(token, Claims::getAudience);
	}

	public Date getLastPasswordDate(String token) {
		final Claims claims = getAllClaimsFromToken(token);
		return new Date((Long)claims.get(CLAIM_KEY_LAST_PASS_DT));

	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token)throws RuntimeException {
		try{
			return Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		}catch(SignatureException se){
			throw new RuntimeException("Invalid token");
		}
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(timeProvider.now());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	//As Spring mobile is removed from spring boot 2 we are not using this
	/*private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }*/

	private String generateAudience() {
		String audience = AUDIENCE_WEB;

		return audience;
	}

	private Boolean ignoreTokenExpiration(String token) {
		String audience = getAudienceFromToken(token);
		return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_LAST_PASS_DT, ((JwtUser) userDetails).getLastPasswordResetDate());
		return doGenerateToken(claims, userDetails.getUsername(), generateAudience());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject, String audience) {
		final Date createdDate = timeProvider.now();
		final Date expirationDate = calculateExpirationDate(createdDate);

		System.out.println("doGenerateToken " + createdDate);

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setAudience(audience)
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getIssuedAtDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		final Date createdDate = timeProvider.now();
		final Date expirationDate = calculateExpirationDate(createdDate);

		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		JwtUser user = (JwtUser) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);
		final Date pswdDt = getLastPasswordDate(token);
		//final Date expiration = getExpirationDateFromToken(token);
		return (
				username.equals(user.getUsername())
				&& !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
				&& pswdDt.equals(user.getLastPasswordResetDate())
				);
	}

	/**
	 * For demo purpose I have increased the token expiration time to 5 months
	 *
	 * createdDate.getTime()+expiration*1000 is replaced with the below code
	 * **/
	private Date calculateExpirationDate(Date createdDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.MONTH,5); //added 5 months
		return c.getTime();

		//    	return new Date(createdDate.getTime() + expiration);

	}
}
