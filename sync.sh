#!/bin/bash

git checkout main;git fetch;git pull;git branch -D deploy;git checkout -b deploy;git cherry-pick 6f7236f2ac504d6784b940d33494e93b04e8cab7;