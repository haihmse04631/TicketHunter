# FROM golang

# WORKDIR /go/src/

# COPY . .

# EXPOSE 80

# RUN go build hello.go

# CMD [ "go", "run", "hello.go" ]

FROM golang

WORKDIR /go/src/

COPY . .

RUN go get github.com/gorilla/mux

EXPOSE 80

CMD [ "go", "run", "/go/src/github/hongsongp97/rest-api/main.go" ]