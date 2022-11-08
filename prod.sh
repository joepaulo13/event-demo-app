#!/bin/bash

git checkout main;git fetch;git pull;git branch -D prod;git checkout -b prod;git cherry-pick ba36997564ae5bc5827a6f0f021761b7e4782944;