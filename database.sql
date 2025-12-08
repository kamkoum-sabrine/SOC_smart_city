-- ======================================================================
-- DATABASE FOR SMART CITY INTEGRATION PROJECT
-- Includes Mobility REST, Emergency gRPC, AirQuality SOAP, Info GraphQL
-- MySQL version
-- ======================================================================

-- Create database
CREATE DATABASE IF NOT EXISTS smartcity;
USE smartcity;

-- ======================================================================
-- 1) MOBILITY REST SERVICE
-- ======================================================================

CREATE TABLE mobility_lines (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(100) NOT NULL,
                                mode VARCHAR(50) NOT NULL -- BUS, METRO, TRAIN
);

CREATE TABLE mobility_stops (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                line_id INT NOT NULL,
                                name VARCHAR(100) NOT NULL,
                                latitude DOUBLE,
                                longitude DOUBLE,
                                FOREIGN KEY (line_id) REFERENCES mobility_lines(id)
);

-- --- Sample data
INSERT INTO mobility_lines (name, mode) VALUES
                                            ('Bus 1', 'BUS'),
                                            ('Bus 2', 'BUS'),
                                            ('Metro A', 'METRO'),
                                            ('Metro B', 'METRO');

INSERT INTO mobility_stops (line_id, name, latitude, longitude) VALUES
                                                                    (1, 'Station Centre', 36.8, 10.17),
                                                                    (1, 'Station République', 36.81, 10.18),
                                                                    (2, 'Station Lac', 36.85, 10.24),
                                                                    (3, 'Station Bab El Khadhra', 36.80, 10.17),
                                                                    (4, 'Station Ennasr', 36.86, 10.20);


-- ======================================================================
-- 2) EMERGENCY gRPC SERVICE
-- ======================================================================

CREATE TABLE emergency_reports (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   type VARCHAR(50) NOT NULL,  -- Accident, Fire, Medical...
                                   description TEXT,
                                   latitude DOUBLE,
                                   longitude DOUBLE,
                                   status VARCHAR(50) DEFAULT 'RECEIVED', -- RECEIVED, IN_PROGRESS, RESOLVED
                                   reported_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample data
INSERT INTO emergency_reports (type, description, latitude, longitude, status) VALUES
                                                                                   ('Accident', 'Car collision near central zone', 36.802, 10.178, 'IN_PROGRESS'),
                                                                                   ('Fire', 'Small fire in building', 36.804, 10.180, 'RECEIVED'),
                                                                                   ('Medical', 'Person fainted in metro station', 36.810, 10.170, 'RESOLVED');


-- ======================================================================
-- 3) AIR QUALITY SOAP SERVICE
-- ======================================================================

CREATE TABLE air_sensors (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             zone VARCHAR(100) NOT NULL,
                             aqi INT,                   -- Air quality index
                             main_pollutant VARCHAR(100),
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample sensors (match your code)
INSERT INTO air_sensors (zone, aqi, main_pollutant) VALUES
                                                        ('Centre', 60, 'NO2'),
                                                        ('Lac', 40, 'PM2.5'),
                                                        ('Ariana', 75, 'O3'),
                                                        ('Marsa', 35, 'SO2');


-- ======================================================================
-- 4) INFO GRAPHQL SERVICE
-- ======================================================================

CREATE TABLE info_zones (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            description TEXT,
                            transport_available BOOLEAN DEFAULT TRUE,
                            active_emergencies INT DEFAULT 0
);

CREATE TABLE info_zone_transport_lines (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           zone_id INT NOT NULL,
                                           line_name VARCHAR(100),
                                           FOREIGN KEY (zone_id) REFERENCES info_zones(id)
);

-- Sample data (consistent with your static data)
INSERT INTO info_zones (name, description, transport_available, active_emergencies) VALUES
                                                                                        ('Centre', 'Main administrative and commercial district.', TRUE, 1),
                                                                                        ('Lac', 'Business and modern area near the lake.', TRUE, 0),
                                                                                        ('Ariana', 'Residential area with busy traffic.', FALSE, 2);

INSERT INTO info_zone_transport_lines (zone_id, line_name) VALUES
                                                               (1, 'Bus 1'),
                                                               (1, 'Bus 2'),
                                                               (2, 'Metro A'),
                                                               (3, 'Bus 3');


-- ======================================================================
-- END OF DATABASE
-- ======================================================================
