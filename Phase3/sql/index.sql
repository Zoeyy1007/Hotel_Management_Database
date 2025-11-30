-- Customer Lookups a customer ID From thier name (5, 12, 13)
CREATE INDEX idx_customer_name ON Customer(fName, lName);

-- Staff Lookups with staff by SSN(6, 7)
CREATE INDEX idx_staff_ssn ON Staff(SSN);

-- Booking Lookups by Hotel & Date (10)
CREATE INDEX idx_booking_hotel_date ON Booking(hotelID, bookingDate);

-- Booking Lookups by Customer (12, 13)
CREATE INDEX idx_booking_customer ON Booking(customer);

-- Booking Date Searches in a date range (11)
CREATE INDEX idx_booking_date ON Booking(bookingDate);

-- Repair Lookups by Company (14, 15)
CREATE INDEX idx_repair_company ON Repair(mCompany);

-- Repair Lookups by Room (16)
CREATE INDEX idx_repair_hotel_room ON Repair(hotelID, roomNo);

-- Maintenance Company Name Lookups to find company ID from the name string (14)
CREATE INDEX idx_mcompany_name ON MaintenanceCompany(name);

-- Room Availability for available/booked room queries Checks (8, 9)
CREATE INDEX idx_room_hotel ON Room(hotelID);
CREATE INDEX idx_booking_hotel ON Booking(hotelID);
