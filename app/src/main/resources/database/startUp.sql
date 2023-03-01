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


MERGE INTO Items (ITEM_ID, ITEM_Title) KEY(ITEM_ID) VALUES (0, 'Example Item');
MERGE INTO Stores (STORE_ID, STORE_Title) KEY(STORE_ID) VALUES  (0, 'Example Store');
MERGE INTO Inventories (Inventory_Id, Item_ID, Store_ID, INVENTORY_quantity) KEY(Inventory_ID) VALUES (0,0,0,1);

-- SELECT * from Inventories 
    -- JOIN Items on Inventories.ItemID = Items.ID
    -- JOIN Stores on Inventories.StoreID = Stores.StoreID;