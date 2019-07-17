CREATE TABLE IF NOT EXISTS `Algorithm`
(
    `id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name`    TEXT,
    `formula` TEXT
);

CREATE INDEX `index_Algorithm_name` ON `Algorithm` (`name`);

CREATE TABLE IF NOT EXISTS `Image`
(
    `filename` TEXT,
    `id`       INTEGER NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `Iteration`
(
    `id`           INTEGER NOT NULL,
    `image_id`     INTEGER NOT NULL,
    `algorithm_id` INTEGER NOT NULL,
    `timeStamp`    TEXT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`image_id`) REFERENCES `Image` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (`algorithm_id`) REFERENCES `Algorithm` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE  INDEX `index_Iteration_id` ON `Iteration` (`id`);

CREATE  INDEX `index_Iteration_image_id_algorithm_id` ON `image_algorithm` (`image_id`, `algorithm_id`)
