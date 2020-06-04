CREATE DATABASE lim_andrew_db;
USE lim_andrew_db;

CREATE TABLE STORE_LOCATION (
	LocationID			int				NOT NULL,
    LocationName		varchar(255)	NOT NULL,
    StoreAddress		varchar(255)	NOT NULL,
    StorePhoneNumber	varchar(255)	NOT NULL,
    CONSTRAINT STORE_LOCATION_PK PRIMARY KEY (LocationID)
);

CREATE TABLE EMPLOYEE (
	EmployeeID			int				NOT NULL,
    EmployeeLastName	varchar(255)	NOT NULL,
    EmployeeFirstName	varchar(255)	NOT NULL,
    EmployeeDOB			date			NOT NULL,
    EmployeeGender		varchar(255)	NOT NULL,
    EmployeePhoneNumber	varchar(255)	NOT NULL,
    EmployeeAddress 	varchar(255) 	NOT NULL,
    hireDate			date			NOT NULL,
    LocationID			int				NOT NULL,
    DepartmentID		int				NOT NULL,
    JobID				int				NOT NULL,
    CONSTRAINT EMPLOYEE_PK PRIMARY KEY (EmployeeID),
	CONSTRAINT STORE_LOCATION_RELATIONSHIP FOREIGN KEY (LocationID)
		REFERENCES STORE_LOCATION (LocationID),
	CONSTRAINT DEPARTMENT_RELATIONSHIP
		FOREIGN KEY (DepartmentID)
		REFERENCES DEPARTMENT (DepartmentID),
	CONSTRAINT JOB_RELATIONSHIP
		FOREIGN KEY (JobID)
		REFERENCES JOB (JobID)
);

CREATE TABLE DEPARTMENT (
	DepartmentID			int				NOT NULL,
    DepartmentName			varchar(255)	NOT NULL,
    DepartmentDescription	varchar(255)	NOT NULL,
    CONSTRAINT DEPARTMENT_PK PRIMARY KEY (DepartmentID)
);

CREATE TABLE JOB (
	JobID			int				NOT NULL,
    JobTitle		varchar(255)	NOT NULL,
    JobDescription	varchar(255)	NOT NULL,
    BaseHourlyRate	double			NOT NULL,
    CONSTRAINT JOB_PK PRIMARY KEY (JobID)
);
    
CREATE TABLE EMPLOYEE_RECORD (
	StartDate			date			NOT NULL,
    EmploymentStatus	varchar(255)	NOT NULL,
    HourlyRate			double			NOT NULL,
    EmployeeID			int				NOT NULL,
    CONSTRAINT EMPLOYEE_RECORD_PK PRIMARY KEY (EmployeeID, StartDate),
    CONSTRAINT EMPLOYEE_RELATIONSHIP FOREIGN KEY (EmployeeID)
		REFERENCES EMPLOYEE (EmployeeID)
);

CREATE TABLE EMPLOYEE_SCHEDULE (
	ScheduleDate		date			NOT NULL,
    StartTime			time			NOT NULL,
    EndTime				time			NOT NULL,
    HoursWorked			double			NULL,
    EmployeeID			int				NOT NULL,
    CONSTRAINT EMPLOYEE_SCHEDULE_PK PRIMARY KEY (EmployeeID, ScheduleDate),
    CONSTRAINT EMPLOYEE_RELATIONSHIP_2 FOREIGN KEY (EmployeeID)
		REFERENCES EMPLOYEE (EmployeeID)
);

CREATE TABLE PRODUCT (
	ProductID			int				NOT NULL,
    ProductName			varchar(255)	NOT NULL,
    ProductDescription	varchar(255)	NOT NULL,
    Price				double			NOT NULL,
    CategoryID			int				NOT NULL,
    BrandID				int				NOT NULL,
    CONSTRAINT PRODUCT_PK PRIMARY KEY (ProductID),
    CONSTRAINT CATEGORY_RELATIONSHIP
		FOREIGN KEY (CategoryID)
        REFERENCES CATEGORY (CategoryID),
	CONSTRAINT BRAND_RELATIONSHIP
		FOREIGN KEY (BrandID)
		REFERENCES BRAND (BrandID)
);

CREATE TABLE INVENTORY (
	Availability		bool			NOT NULL,
    Stock				int				NOT NULL,
    LocationID			int 			NOT NULL,
    ProductID			int				NOT NULL,
    CONSTRAINT INVENTORY_PK PRIMARY KEY (LocationID, ProductID),
    CONSTRAINT STORE_LOCATION_RELATIONSHIP_2 FOREIGN KEY (LocationId)
		REFERENCES STORE_LOCATION (LocationID),
	CONSTRAINT PRODUCT_RELATIONSHIP FOREIGN KEY (ProductID)
		REFERENCES PRODUCT (ProductID)
);

CREATE TABLE CATEGORY (
	CategoryID				int				NOT NULL,
    CategoryName			varchar(255)	NOT NULL,
    CategoryDescription		varchar(255)	NOT NULL,
    CONSTRAINT CATEGORY_PK PRIMARY KEY (CategoryID)
);

CREATE TABLE BRAND (
	BrandID					int				NOT NULL,
	BrandName				varchar(255)	NOT NULL,
    BrandDescription		varchar(255)	NOT NULL,
    CONSTRAINT BRAND_PK	PRIMARY KEY (BrandID)
);
    
CREATE TABLE CUSTOMER (
	CustomerID			int				NOT NULL,
    CustomerLastName	varchar(255)	NOT NULL,
    CustomerFirstName	varchar(255)	NOT NULL,
    CustomerDOB			date			NOT NULL,
	CustomerGender		varchar(255)	NOT NULL,
    CustomerAddress		varchar(255)	NOT NULL,
    CustomerPhoneNumber	varchar(255)	NOT NULL,
    CONSTRAINT CUSTOMER_PK PRIMARY KEY (CustomerID)
);

CREATE TABLE `ORDER` (
	OrderID			int			NOT NULL,
    OrderDate		date		NOT NULL,
    OrderTime		time		NOT NULL,
    OrderTotalCost	double		NOT NULL,
    CustomerID		int			NOT NULL,
    CONSTRAINT ORDER_PK PRIMARY KEY (OrderID),
    CONSTRAINT CUSTOMER_RELATIONSHIP FOREIGN KEY (CustomerID)
		REFERENCES CUSTOMER (CustomerID)
);

CREATE TABLE ORDER_PRODUCT (
	Quantity		int			NOT NULL,
    Cost			double		NOT NULL,
    OrderID			int			NOT NULL,
    ProductID		int			NOT NULL,
    CONSTRAINT ORDER_PRODUCT_PK PRIMARY KEY (OrderID, ProductID),
    CONSTRAINT ORDER_RELATIONSHIP FOREIGN KEY (OrderID)
		REFERENCES `ORDER` (OrderID),
	CONSTRAINT PRODUCT_RELATIONSHIP_2 FOREIGN KEY (ProductID)
		REFERENCES PRODUCT (ProductID)
);

CREATE TABLE PRODUCT_REVIEW (
	Rating			int				NOT NULL,
    `Comment`		varchar(255)	NULL,
    ReviewDate		date			NOT NULL,
    ProductID		int				NOT NULL,
	CustomerID		int				NOT NULL,
    CONSTRAINT PRODUCT_REVIEW_PK PRIMARY KEY (ProductID, CustomerID),
    CONSTRAINT PRODUCT_RELATIONSHIP_3 FOREIGN KEY (ProductID)
		REFERENCES PRODUCT (ProductID),
	CONSTRAINT CUSTOMER_RELATIONSHIP_2 FOREIGN KEY (CustomerID)
		REFERENCES CUSTOMER (CustomerID)
);

CREATE TABLE `RETURN` (
	ReturnID			int				NOT NULL,
    ReturnDate			date			NOT NULL,
	AmountRefunded		double			NOT NULL,
    ReasonForReturn		varchar(255)	NOT NULL,
    ProductCondition	varchar(255)	NOT NULL,
    CustomerID			int				NOT NULL,
    OrderID				int				NOT NULL,
    CONSTRAINT RETURN_PK PRIMARY KEY (ReturnID),
    CONSTRAINT CUSTOMER_RELATIONSHIP_3 FOREIGN KEY (CustomerID)
		REFERENCES CUSTOMER (CustomerID),
	CONSTRAINT ORDER_RELATIONSHIP_2 FOREIGN KEY (OrderID)
		REFERENCES `ORDER` (OrderID)
);

INSERT INTO STORE_LOCATION VALUES (1, "Wildwood", "566 Wildwood Street, Barberton, OH 44203", "330-706-5477");
INSERT INTO STORE_LOCATION VALUES (2, "Pinnickinnick", "3909 Pinnickinnick Street, New Brunswick, NJ 08901", "732-846-6107");
INSERT INTO STORE_LOCATION VALUES (3, "Tipple", "1733 Tipple Road, Philadelphia, PA 19143", "215-730-7563");

INSERT INTO EMPLOYEE VALUES (1, "Tate", "Mary", "1983-12-13", "Female", "334-460-6451", "2395 Turkey Pen Lane, Montgomery, AL 36104", "2000-03-17", 1, 2, 3);
INSERT INTO EMPLOYEE VALUES (2, "Bequette", "James", "1996-07-08", "Male", "970-327-6775", "3639 Timberbrook Lane, Norwood, CO 81423", "2000-06-05", 2, 3, 1);
INSERT INTO EMPLOYEE VALUES (3, "Eugene", "Brian", "1964-11-12", "Male", "786-888-7286", "1513 Lamberts Branch Road, Doral, FL 33166", "2001-10-11", 1, 2, 2);

INSERT INTO DEPARTMENT VALUES (1, "Clothing", "Clothing is items worn on the body.");
INSERT INTO DEPARTMENT VALUES (2, "Furniture", "Large movable equipment, such as tables and chairs, used to make a house, office, or other space suitable for living or working.");
INSERT INTO DEPARTMENT VALUES (3, "Home Appliances", "A home appliance, domestic appliance or household appliance, is a device which assists in household functions such as cooking, cleaning and food preservation.");

INSERT INTO JOB VALUES (1, "Shop Assistant", "A sales assistant looks after customers when they are shopping.", 27.00);
INSERT INTO JOB VALUES (2, "Cashier", "A retail cashier or simply a cashier is a person who handles the cash register at various locations such as the point of sale in a retail store.", 13.00);
INSERT INTO JOB VALUES (3, "Manager", "A person responsible for controlling or administering all or part of a company or similar organization.", 40.00);

INSERT INTO EMPLOYEE_RECORD VALUES ("2001-11-02", "Full-time", 32.00, 1);
INSERT INTO EMPLOYEE_RECORD VALUES ("2003-05-25", "Part-time", 28.00, 2);
INSERT INTO EMPLOYEE_RECORD VALUES ("2003-11-12", "Temporary", 11.00, 3);

INSERT INTO EMPLOYEE_SCHEDULE VALUES ("2005-03-26", "11:05:00", "16:10:00", 5.00, 1);
INSERT INTO EMPLOYEE_SCHEDULE VALUES ("2006-01-19", "09:50:00", "15:45:00", 8.00, 2);
INSERT INTO EMPLOYEE_SCHEDULE VALUES ("2007-08-14", "10:55:00", "15:25:00", 7.00, 3);

INSERT INTO PRODUCT VALUES (1, "Desk", "A piece of furniture with a flat or sloped surface and typically with drawers, at which one can read, write, or do other work.", 52.00, 1, 2);
INSERT INTO PRODUCT VALUES (2, "Watch", "A small timepiece worn typically on a strap on one's wrist.", 83.00, 3, 2);
INSERT INTO PRODUCT VALUES (3, "Whiteboard", "A wipeable board with a white surface used for teaching or presentations.", 26.00, 1, 2);

INSERT INTO INVENTORY VALUES (0, 583, 2, 1);
INSERT INTO INVENTORY VALUES (1, 312, 3, 3);
INSERT INTO INVENTORY VALUES (1, 243, 3, 1);

INSERT INTO CATEGORY VALUES (1, "A", "Category A");
INSERT INTO CATEGORY VALUES (2, "B", "Category B");
INSERT INTO CATEGORY VALUES (3, "C", "Category C");

INSERT INTO BRAND VALUES (1, "Simple Truth", "Simple Truth® and Simple Truth Organic® deliver affordable and delicious foods that can be enjoyed the way nature intended.");
INSERT INTO BRAND VALUES (2, "Kroger", "Kroger Brand gives you top-shelf quality at budget-friendly prices.");
INSERT INTO BRAND VALUES (3, "HemisFares", "We traveled the globe to bring you an extraordinary collection of regional specialties from Italy, Jamaica, Japan and Spain.");

INSERT INTO CUSTOMER VALUES (1, "Davis", "Earl", "1983-06-07", "Male", "3472 Jacobs Street, Pittsburgh, PA 15219", "412-288-3922");
INSERT INTO CUSTOMER VALUES (2, "Money", "Virgil", "1966-12-28", "Male", "2076 Lilac Lane, Douglas, GA 31533", "912-389-2829");
INSERT INTO CUSTOMER VALUES (3, "Johnson", "John", "1982-01-11", "Male", "2771 White River Way, Clearfield, UT 84015", "801-525-9093");

INSERT INTO `ORDER` VALUES (1, "2007-08-26", "10:00:00", 134.00, 1);
INSERT INTO `ORDER` VALUES (2, "2007-09-03", "15:05:0", 103.00, 3);
INSERT INTO `ORDER` VALUES (3, "2008-04-16", "13:20:00", 105.00, 1);

INSERT INTO ORDER_PRODUCT VALUES (6, 12.00, 2, 3);
INSERT INTO ORDER_PRODUCT VALUES (4, 29.00, 1, 1);
INSERT INTO ORDER_PRODUCT VALUES (9, 23.00, 3, 1);

INSERT INTO PRODUCT_REVIEW VALUES (4, "OK.", "2009-08-24", 2, 3);
INSERT INTO PRODUCT_REVIEW VALUES (2, "Bad.", "2010-06-08", 1, 2);
INSERT INTO PRODUCT_REVIEW VALUES (3, "Good.", "2011-03-13", 1, 1);

INSERT INTO `RETURN` VALUES (1, "2012-10-28", 96.00, "Incorrect Product or Size Ordered", "Bad.", 3, 2);
INSERT INTO `RETURN` VALUES (2, "2013-04-04", 57.00, "Product No Longer Needed", "OK.", 2, 3);
INSERT INTO `RETURN` VALUES (3, "2013-05-05", 16.00, "Product Did Not Meet Customer’s Expectations", "OK.", 2, 1);
