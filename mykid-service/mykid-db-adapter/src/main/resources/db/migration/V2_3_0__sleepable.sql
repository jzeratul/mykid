CREATE TABLE sleep (
      ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT
    , USR_ID BIGINT
    , CREATED_AT DATETIME
    , START_SLEEP DATETIME
    , END_SLEEP DATETIME
    , INDEX (CREATED_AT)
    , INDEX (START_SLEEP)
    , INDEX (END_SLEEP)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;