CREATE TABLE `applicationvoicexmlurlmap` (
  `applicationname` varchar(80) NOT NULL,
  `voicexmlurl` varchar(256) NOT NULL,
  `artistShortCode` varchar(45) NOT NULL,
  PRIMARY KEY (`applicationname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
