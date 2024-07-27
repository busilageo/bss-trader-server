-- Create sequence for Sticker ID
CREATE SEQUENCE IF NOT EXISTS public.sticker_id_seq
START WITH 1
INCREMENT BY 1;

-- Create Sticker table
CREATE TABLE IF NOT EXISTS public.sticker
(
    id bigint NOT NULL DEFAULT nextval('sticker_id_seq'::regclass),
    name character varying(100) NOT NULL COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    type character varying(50) NOT NULL COLLATE pg_catalog."default",
    CONSTRAINT sticker_pkey PRIMARY KEY (id)
    );

-- Optionally, add a check constraint to ensure the type field only allows specific values
-- ALTER TABLE public.sticker
-- ADD CONSTRAINT check_sticker_type CHECK (type IN ('CUB_SKIN', 'HIVE_SKIN', 'VOUCHER', 'HIVE_STICKER'));
