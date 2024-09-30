#!/usr/bin/env bash
set -euo pipefail

tmpfile=$(mktemp --tmpdir "getneed.$$.XXXXXX")
trap 'rm -f -- "$tmpfile"' EXIT
trap 'exit' INT TERM

curl() {
    command curl -sS --fail-with-body -w '%{stderr}HTTP %{response_code}\n' "$@" >"$tmpfile"
    cat "$tmpfile"
}

# returns id
create_need() {
    curl 'http://localhost:8080/needs' --json '@-' | tee /dev/tty | jq -r '.id'
}

echo "get all needs when none exist"
curl "http://localhost:8080/needs"

echo -e "\n\ncreate first need:"
id1=$(create_need <<'EOF'
{
    "name": "NeedA1",
    "description": "Descr1",
    "demandRating": 1.0,
    "cost": 11.0,
    "quantity": 1
}
EOF
)

echo -e "\n\ncreate second need:"
create_need <<'EOF' >/dev/null
{
    "name": "NeedA2",
    "description": "Descr2",
    "demandRating": 2.0,
    "cost": 22.0,
    "quantity": 2
}
EOF

echo -e "\n\nget one need:"
curl "http://localhost:8080/needs/$id1"

echo -e "\n\nget a need that doesn't exist:"
! curl "http://localhost:8080/needs/notFound"

echo -e "\n\nget all needs"
curl "http://localhost:8080/needs"
