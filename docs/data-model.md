## DDL for Data Model
``` sql
CREATE TABLE IF NOT EXISTS `Algorithm`
(
    `id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name`    TEXT,
    `detail`  TEXT COLLATE NOCASE,
    `example` TEXT,
    `formula` TEXT

);

CREATE INDEX 'index_Algorithm_name' ON `Algorithm` (`name`);
CREATE INDEX `index_Algorithm_detail` ON `Algorithm` (`detail`);

CREATE TABLE IF NOT EXISTS `Image`
(
    `id`        INTEGER NOT NULL,
    `filename`  TEXT,
    `timestamp` INTEGER,
    PRIMARY KEY (`id`)
);

CREATE INDEX `index_Image_timestamp` ON `Image` (`timestamp`);



CREATE TABLE IF NOT EXISTS `Iteration`
(
    `iteration_id` INTEGER NOT NULL,
    `algorithm_id` INTEGER NOT NULL,
    `image_id`     INTEGER NOT NULL,
    `timestamp`    INTEGER,
    `iterations`   TEXT,
    PRIMARY KEY (`iteration_id`),
    FOREIGN KEY (`image_id`) REFERENCES `Image` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (`algorithm_id`) REFERENCES `Algorithm` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX `index_Iteration_iteration_id` ON `Iteration` (`iteration_id`);

CREATE INDEX `index_Iteration_image_id_algorithm_id` ON `Iteration` (`image_id`, `algorithm_id`);

CREATE INDEX `index_Iteration_timestamp` ON `Iteration` (`timestamp`);
CREATE TABLE IF NOT EXISTS room_master_table
(
    id            INTEGER PRIMARY KEY,
    identity_hash TEXT
);
INSERT OR
REPLACE INTO room_master_table (id, identity_hash)
VALUES (42, 'efa1f38fa9d3f1a21c351dfc3c919a43');
```

