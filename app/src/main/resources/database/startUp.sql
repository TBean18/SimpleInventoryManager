CREATE TABLE if not exists Items (
    ITEM_ID int NOT NULL AUTO_INCREMENT,
    ITEM_Title varchar(255) NOT NULL,
    ITEM_Description CLOB(1k),
    ITEM_Image BLOB(10K),
    PRIMARY KEY (ITEM_ID)
);

CREATE TABLE if not exists Stores (
    STORE_ID int NOT NULL AUTO_INCREMENT,
    STORE_Title varchar(255),
    STORE_Description CLOB(1k),
    PRIMARY KEY (STORE_ID)
);

-- Many to Many Mapping
CREATE TABLE if not exists Inventories (
    Inventory_ID int NOT NULL AUTO_INCREMENT,
    Item_ID int NOT NULL,
    Store_ID int NOT NULL,
    INVENTORY_quantity int not NULL,
    PRIMARY KEY(Inventory_ID),
    FOREIGN KEY (Item_ID) REFERENCES Items,
    FOREIGN KEY (Store_ID) REFERENCES Stores
);


INSERT INTO Items (ITEM_ID, ITEM_Title) VALUES (0, 'Example Item');
INSERT INTO Stores (STORE_ID, STORE_Title) VALUES  (0, 'Example Store');
INSERT INTO Inventories (Item_ID, Store_ID, INVENTORY_quantity) VALUES (0,0,1);

-- SELECT * from Inventories 
    -- JOIN Items on Inventories.ItemID = Items.ID
    -- JOIN Stores on Inventories.StoreID = Stores.StoreID;