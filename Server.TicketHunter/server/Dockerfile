FROM golang

WORKDIR /go/src/

COPY . .

RUN go get github.com/gorilla/mux

EXPOSE 80

# CMD [ "go", "run", "/go/src/hello.go" ]

CMD [ "go", "run", "/go/src/github/hongsongp97/rest-api/main.go" ]