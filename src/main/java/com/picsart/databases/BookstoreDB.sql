--creating books table in bookstore database
CREATE TABLE Books(
                      BookID SERIAL PRIMARY KEY,
                      Title TEXT NOT NULL,
                      Author VARCHAR(40) NOT NULL,
                      Genre VARCHAR(30) NOT NULL,
                      Price REAL NOT NULL CHECK(Price > 0),
                      QuantityInStock INTEGER NOT NULL CHECK(QuantityInStock >= 0)
);

INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book1', 'Author1', 'Genre1', 100.5, 50);
INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book2', 'Author2', 'Genre2', 150.5, 45);
INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book3', 'Author3', 'Genre3', 200.5, 40);
INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book4', 'Author4', 'Genre4', 250.5, 35);
INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book5', 'Author5', 'Genre5', 300.5, 60);
INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book6', 'Author6', 'Genre6', 350.5, 40);
INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book7', 'Author7', 'Genre7', 400.5, 40);
INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book8', 'Author8', 'Genre8', 450.5, 40);
INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book9', 'Author9', 'Genre9', 500.5, 40);
INSERT INTO Books(Title, Author, Genre, Price, QuantityInStock) VALUES ('Book10', 'Author10', 'Genre10', 550.5, 40);


--creating customers table in bookstore database
CREATE TABLE Customers(
                          CustomerID SERIAL PRIMARY KEY,
                          Name VARCHAR(20) NOT NULL,
                          Email VARCHAR(60) UNIQUE,
                          Phone VARCHAR(20) NOT NULL
);

INSERT INTO Customers(Name, Email, Phone) VALUES ('Customers1', 'email1@gmail.com', '+37494111111');
INSERT INTO Customers(Name, Email, Phone) VALUES ('Customers2', 'email2@gmail.com', '+37494222222');
INSERT INTO Customers(Name, Email, Phone) VALUES ('Customers3', 'email3@gmail.com', '+37494333333');
INSERT INTO Customers(Name, Email, Phone) VALUES ('Customers4', 'email4@gmail.com', '+37494444444');
INSERT INTO Customers(Name, Email, Phone) VALUES ('Customers5', 'email5@gmail.com', '+37494555555');

--creating sales table that will hold foreign keys referencing primary keys of books and customers tables
CREATE TABLE Sales (
                       SaleID SERIAL PRIMARY KEY,
                       BookID INTEGER,
                       CustomerID INTEGER,
                       DateOfSale DATE,
                       QuantitySold INTEGER NOT NULL CHECK(QuantitySold >= 0),
                       TotalPrice REAL NOT NULL CHECK(TotalPrice >= 0),
                       CONSTRAINT fk_book FOREIGN KEY (BookID) REFERENCES Books(BookID) ON DELETE SET NULL,
                       CONSTRAINT fk_customer FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE SET NULL
);

--creating a trigger to update quantity of books in store
CREATE OR REPLACE FUNCTION update_books_quantity_in_stock()
    RETURNS TRIGGER AS $$
BEGIN
UPDATE Books
SET QuantityInStock = QuantityInStock - NEW.QuantitySold
WHERE BookID = NEW.BookID;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_books_quantity
    AFTER INSERT ON Sales
    FOR EACH ROW
    EXECUTE FUNCTION update_books_quantity_in_stock();



--query for retrieving a list of all books sold, including the book title, customer name, and date of sale
SELECT Sales.DateOfSale AS DateOfSale, Books.Title AS BookTitle, Customers.Name as CustomerName
FROM Sales
         JOIN Books ON Sales.BookID = Books.BookID
         JOIN Customers ON Sales.CustomerID = Customers.CustomerID;

--query for finding the total revenue generated from each genre of books
SELECT Books.Genre, SUM(Sales.TotalPrice) as Revenue
FROM Sales
         JOIN Books ON Sales.BookID = Books.BookID
GROUP BY Books.Genre;

