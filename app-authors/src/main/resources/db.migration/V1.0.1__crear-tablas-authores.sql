CREATE TABLE IF NOT EXISTS public.authors (
    id bigint NOT NULL,
    name varchar(255),
    version integer NOT NULL DEFAULT 1,
    CONSTRAINT authors_pkey PRIMARY KEY (id)
);

INSERT INTO public.authors (id, name, version) VALUES
(1, 'Joshua Bloch', 1),
(2, 'Herbert Schildt', 1),
(3, 'Kathy Sierra', 1),
(4, 'Brian Goetz', 1),
(5, 'Robert C. Martin', 1)
ON CONFLICT (id) DO NOTHING;
