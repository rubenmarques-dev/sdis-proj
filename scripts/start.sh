#!/bin/bash

echo "running first peer"
gnome-terminal -x bash -c "./peers/start_peer1.sh"

echo "running second peer"
gnome-terminal -x bash -c "./peers/start_peer2.sh"

echo "running third peer"
gnome-terminal -x bash -c "./peers/start_peer3.sh"

echo "done."