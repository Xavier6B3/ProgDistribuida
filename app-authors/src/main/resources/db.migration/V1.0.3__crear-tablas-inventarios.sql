CREATE TABLE IF NOT EXISTS public.inventory (
    book_isbn varchar(255) NOT NULL,
    sold integer NOT NULL,
    supplied integer NOT NULL,
    version integer NOT NULL DEFAULT 1,
    CONSTRAINT inventory_pkey PRIMARY KEY (book_isbn),
    CONSTRAINT fk_inventory_books FOREIGN KEY (book_isbn) REFERENCES public.books(isbn)
);

INSERT INTO public.inventory (sold, supplied, version, book_isbn) VALUES
(10, 50, 1, '978-0134686097')
ON CONFLICT (book_isbn) DO NOTHING;
