#!/usr/bin/python

# Import the CGI, string, sys modules
import cgi, string, sys, os, re, random
import cgitb; cgitb.enable()  # for troubleshooting
import sqlite3
import time
import datetime
import json

#Get Databasedir
MYLOGIN="wang1247"
DATABASE="/homes/"+MYLOGIN+"/apache/htdocs/SolarCar/car_info.db"

##############################################################
def get_latest_data():
  sys.stderr.write("entering new tweet.\n")
  conn = sqlite3.connect(DATABASE)
  c = conn.cursor()

  c.execute("SELECT count(*) FROM data")
  row = c.fetchone()
  count = row[0]
  c.execute("SELECT speed, batterycharge, arraypower, motorcurrent, batterycurrent FROM data WHERE dataid = {0}".format(count))
  row = c.fetchone()
  data = [ {'speed':row[0], 'batterycharge':row[1], 'arraypower':row[2],
            'motorcurrent':row[3], 'batterycurrent':row[4]} ]

  sys.stderr.write("entering new tweet.\n")
  print("Content-Type: application/json\n\n")
  data_string = json.dumps(data)
  sys.stderr.write("entering new tweet.\n")
  print data_string
  sys.stderr.write("entering new tweet.\n")

  conn.commit()
  conn.close()


##############################################################
def add_data(form):
  conn = sqlite3.connect(DATABASE)
  c = conn.cursor()
  
  c.execute('SELECT COUNT(*) FROM data')
  row = c.fetchone()
  dataid=row[0]
  
  if "time" in form:
    timestamp = int(time.time())
  else:
    timestamp=None

  if "speed" in form:
    speed=form["speed"].value
  else:
    speed=None

  if "batterycharge" in form:
    batterycharge=form["batterycharge"].value
  else:
    batterycharge=None

  if "arraypower" in form:
    arraypower=form["arraypower"].value
  else:
    arraypower=None

  if "motorcurrent" in form:
    motorcurrent=form["motorcurrent"].value
  else:
    motorcurrent=None

  if "batterycurrent" in form:
    batterycurrent=form["batterycurrent"].value
  else:
    batterycurrent=None
  
  t = (dataid, timestamp, speed, batterycharge, arraypower, motorcurrent, batterycurrent)
  c.execute('INSERT INTO data VALUES (?,?,?,?,?,?,?)', t)

  conn.commit()
  conn.close()

##############################################################
def main():
  sys.stderr.write("entering new tweet.\n")
  form = cgi.FieldStorage()
  if "action" in form:
    action=form["action"].value
    if action == "get_latest_data":
      get_latest_data()
    if action == "post":
      add_data(form)

main()