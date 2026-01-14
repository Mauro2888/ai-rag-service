#!/bin/bash

PORT=8080

PIDS=$(lsof -ti tcp:$PORT)

if [ -z "$PIDS" ]; then
  echo "Nessun processo in ascolto sulla porta $PORT."
  exit 0
fi

echo "Terminazione gentile dei processi: $PIDS"
kill $PIDS
sleep 1

# Controlla se qualcuno è ancora vivo
PIDS_AFTER=$(lsof -ti tcp:$PORT)
if [ -n "$PIDS_AFTER" ]; then
  echo "Forzo la chiusura dei processi rimasti: $PIDS_AFTER"
  kill -9 $PIDS_AFTER
fi
