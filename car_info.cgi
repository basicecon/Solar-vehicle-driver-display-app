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
DATABASE="/homes/"+MYLOGIN+"/Solar-vehicle-driver-display-app/car_info.db"

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
def main():
  sys.stderr.write("entering new tweet.\n")
  form = cgi.FieldStorage()
  if "action" in form:
    action=form["action"].value
    if action == "get_latest_data":
      get_latest_data()

main()