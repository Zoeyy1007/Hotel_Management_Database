/*-- 1. addCustomer 
INSERT INTO Customer(customerID, fName, lName, Address, phNo, DOB, gender) 
VALUES (2001, 'Test', 'User', '123 Test St', 5550000, '1990-01-01', 'Male');

-- 2. addRoom 
INSERT INTO Room(hotelID, roomNo, roomType) 
VALUES (1, 999, 'Suite');


-- 3. addMaintenanceCompany 
INSERT INTO MaintenanceCompany(cmpID, name, address, isCertified) 
VALUES (500, 'TestCompany', 'Test Address', TRUE);

-- 4. addRepair 
INSERT INTO Repair(rID, hotelID, roomNo, mCompany, repairDate, description, repairType) 
VALUES (2001, 1, 1, 1, '2023-01-01', 'Test Repair', 'Small');

-- 5. bookRoom 
SELECT customerID FROM Customer WHERE fName = 'rzqs' AND lName = 'eyeg';
SELECT MAX(bID) FROM Booking;
INSERT INTO Booking(bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price) 
VALUES (5001, 1, 1, 1, '2023-11-01', 2, 150.00);

-- 6. assignHouseCleaningToRoom 
SELECT employerID FROM Staff WHERE SSN = 1;
SELECT MAX(asgID) FROM Assigned;
INSERT INTO Assigned(asgID, staffID, hotelID, roomNo) 
VALUES (5001, 1, 1, 1);

-- 7. repairRequest 
SELECT role FROM Staff WHERE SSN = 1;
SELECT employerID FROM Staff WHERE SSN = 1;
SELECT MAX(reqID) FROM Request;
INSERT INTO Request(reqID, managerID, repairID, requestDate, description) 
VALUES (5001, 1, 1, '2023-11-01', 'Test Request');
*/
-- 8. numberOfAvailableRooms 
SELECT (SELECT COUNT(*) FROM Room WHERE hotelID = 1) - 
       (SELECT COUNT(*) FROM Booking WHERE hotelID = 1 AND bookingDate = '2015-05-12');

-- 9. numberOfBookedRooms 
SELECT COUNT(*) FROM Booking WHERE hotelID = 1 AND bookingDate = '2015-05-12';

-- 10. listHotelRoomBookingsForAWeek 
SELECT roomNo, bookingDate FROM Booking 
WHERE hotelID = 1 
AND roomNo = 1 
AND bookingDate >= '2015-05-12' 
AND bookingDate < '2015-05-12'::date + interval '7 days';

-- 11. topKHighestRoomPriceForADateRange 
SELECT R.hotelID, R.roomNo, MAX(B.price) AS max_price 
FROM Room R JOIN Booking B ON R.hotelID = B.hotelID AND R.roomNo = B.roomNo 
WHERE B.bookingDate >= '2000-01-01' AND B.bookingDate <= '2020-12-31' 
GROUP BY R.hotelID, R.roomNo 
ORDER BY max_price DESC 
LIMIT 5;

/*-- 12. topKHighestPriceBookingsForACustomer 
SELECT B.hotelID, B.roomNo, B.bookingDate, B.price 
FROM Booking B, Customer C 
WHERE B.customer = C.customerID 
AND C.fName = 'rzqs' AND C.lName = 'eyeg' 
ORDER BY B.price DESC LIMIT 5;*/

-- 13. totalCostForCustomer 
SELECT SUM(B.price) 
FROM Booking B, Customer C 
WHERE B.customer = C.customerID 
AND B.hotelID = 1 
AND C.fName = 'rzqs' AND C.lName = 'eyeg' 
AND B.bookingDate >= '2000-01-01' 
AND B.bookingDate <= '2020-12-31';

-- 14. listRepairsMade 
SELECT R.repairType, R.hotelID, R.roomNo 
FROM Repair R, MaintenanceCompany M 
WHERE R.mCompany = M.cmpID 
AND M.name = 'iqcq';

-- 15. topKMaintenanceCompany 
SELECT M.name, COUNT(R.rID) AS repair_count 
FROM MaintenanceCompany M 
LEFT JOIN Repair R ON M.cmpID = R.mCompany 
GROUP BY M.name 
ORDER BY repair_count DESC 
LIMIT 5;

-- 16. numberOfRepairsForEachRoomPerYear 
SELECT EXTRACT(YEAR FROM R.repairDate) AS repair_year, COUNT(R.rID) AS repair_count 
FROM Repair R 
WHERE R.hotelID = 1 AND R.roomNo = 1 
GROUP BY repair_year 
ORDER BY repair_year;