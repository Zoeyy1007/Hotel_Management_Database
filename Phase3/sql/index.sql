DROP INDEX IF EXISTS idx_customer_name;
DROP INDEX IF EXISTS idx_staff_ssn;
DROP INDEX IF EXISTS idx_booking_hotel_date;
DROP INDEX IF EXISTS idx_booking_customer;
DROP INDEX IF EXISTS idx_booking_date;
DROP INDEX IF EXISTS idx_repair_company;
DROP INDEX IF EXISTS idx_repair_hotel_room;
DROP INDEX IF EXISTS idx_mcompany_name;
DROP INDEX IF EXISTS idx_room_hotel;

-- Customer Lookups 1, 7, 8 
CREATE INDEX idx_customer_name
ON Customer
USING BTREE
(fName, lName);

-- Staff Lookups 2
CREATE INDEX idx_staff_ssn
ON Staff
USING BTREE
(SSN);

-- Booking Lookups by Hotel & Date 3, 4, 5
CREATE INDEX idx_booking_hotel_date
ON Booking
USING BTREE
(hotelID, bookingDate);

-- Booking Lookups by Customer 7, 8
CREATE INDEX idx_booking_customer
ON Booking
USING BTREE
(customer);

-- General Booking Date Range 6
CREATE INDEX idx_booking_date
ON Booking
USING BTREE
(bookingDate);

-- Repair Lookups by Company 9, 10
CREATE INDEX idx_repair_company
ON Repair
USING BTREE
(mCompany);

-- Repair Lookups by Room 11
CREATE INDEX idx_repair_hotel_room
ON Repair
USING BTREE
(hotelID, roomNo);

-- Maintenance Company Name Lookups 9
CREATE INDEX idx_mcompany_name
ON MaintenanceCompany
USING BTREE
(name);

-- Room Availability 3 Lookups
CREATE INDEX idx_room_hotel
ON Room
USING BTREE
(hotelID);
