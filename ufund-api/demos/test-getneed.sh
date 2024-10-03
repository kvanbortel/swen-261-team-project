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

echo -e "\n\nPress enter to continue..."
read

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

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\ncreate second need:"
id2=$(create_need <<'EOF'
{
    "name": "NeedA2",
    "description": "Descr2",
    "demandRating": 2.0,
    "cost": 22.0,
    "quantity": 2
}
EOF
)

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\ncreate third need:"
id3=$(create_need <<'EOF' >/dev/null
{
    "name": "NeedB2",
    "description": "Descr3",
    "demandRating": 3.0,
    "cost": 33.0,
    "quantity": 3
}
EOF
)

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\nget one need, NeedA1:"
curl "http://localhost:8080/needs/$id1"

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\nget a need that doesn't exist, need with id \"notFound\":"
! curl "http://localhost:8080/needs/notFound"

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\nget all needs"
curl "http://localhost:8080/needs"

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\nsearch for needs that exist, searching \"NeedA\""
curl "http://localhost:8080/needs/?search=NeedA"

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\nsearch for a need that doesn't exist, searching \"NeedC\""
curl "http://localhost:8080/needs/?search=NeedC"

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\nall needs before deletion"
curl "http://localhost:8080/needs"

echo -e "\n\ndelete a need, \"NeedA1\""
curl -X DELETE "http://localhost:8080/needs/$id1"

echo -e "\n\nall needs after deletion. \"NeedA1\" is gone."
curl "http://localhost:8080/needs"

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\ndelete need 'c2' with incorrect id"
! curl -i -X DELETE 'http://localhost:8080/needs/c2'

echo -e "\n\nall needs again. Same as before"
curl "http://localhost:8080/needs"

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\nall needs before \"NeedA2\" is updated"
curl "http://localhost:8080/needs"

echo -e "\n\nupdate need \"NeedA2\" to \"UpdatedNeed\""
curl.exe -i -X PUT -H "Content-Type:application/json" "http://localhost:8080/needs" -d "{\"id\": \"$id2\",\"name\": \"UpdatedNeed\"}"

echo -e "\n\nall needs after"
curl "http://localhost:8080/needs"

echo -e "\n\nPress enter to continue..."
read

echo -e "\n\nupdate need \"NeedA2\" with incorrect id"
! curl.exe -i -X PUT -H "Content-Type:application/json" "http://localhost:8080/needs" -d "{\"id\": \"notId\",\"name\": \"IncorrectNeed\"}"

echo -e "\n\nall needs after. Nothing has changed."
curl "http://localhost:8080/needs"
