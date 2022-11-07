#!/bin/bash

git checkout main;git fetch;git pull;git branch -D dev;git checkout -b dev;git cherry-pick 1276fe78cd19f334198c0f2b9ba878f46261b64a;