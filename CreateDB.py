#!/usr/bin/python

import sqlite3
conn = sqlite3.connect('car_info.db')

c = conn.cursor()

# Turn on foreign key support
c.execute("PRAGMA foreign_keys = ON")

# Create data table
c.execute('''CREATE TABLE data	
	     (dataid INTEGER NOT NULL,
	      time INTEGER,
	      speed INTEGER,
	      batterycharge INTEGER,
	      arraypower INTEGER,
	      motorcurrent INTEGER,
	      batterycurrent INTEGER,
	      PRIMARY KEY(dataid))''')

conn.commit()
conn.close()
