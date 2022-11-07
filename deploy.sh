#!/bin/bash

# This script is used to deploy the application to the server
./mvnw -DskipTests package appengine:deploy