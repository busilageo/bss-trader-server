COPY sticker(name, description, type)
    FROM '${STICKER_CSV_PATH}'
    DELIMITER ','
    CSV HEADER;
