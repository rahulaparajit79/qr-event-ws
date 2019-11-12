CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eventName` varchar(250) NOT NULL,
  `eventDesc` text NOT NULL,
  `eventStartTime` datetime NOT NULL,
  `eventEndTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partyId` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `mobileNo` varchar(13) NOT NULL,
  `emailId` varchar(250) DEFAULT NULL,
  `username` varchar(250) DEFAULT NULL,
  `password` varchar(250) NOT NULL,
  `userImage` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobileNo_UNIQUE` (`mobileNo`),
  UNIQUE KEY `emailId_UNIQUE` (`emailId`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

