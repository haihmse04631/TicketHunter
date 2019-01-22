package main

import (
    "encoding/json"
    "github.com/gorilla/mux"
    "log"
    "net/http"
    "fmt"

    Model "github/hongsongp97/rest-api/model"
)

type Person Model.Person

type Address Model.Address

var people []Person

// Display all from the people var
func GetPeople(w http.ResponseWriter, r *http.Request) {
    json.NewEncoder(w).Encode(people)
}

// Display a single data
func GetPerson(w http.ResponseWriter, r *http.Request) {
    params := mux.Vars(r)
    fmt.Println(params["id"])
    
    for _, item := range people {
        fmt.Println(item)
        if item.ID == params["id"] {
            json.NewEncoder(w).Encode(item)
            return
        }
    }
    json.NewEncoder(w).Encode(&Person{})
}

// create a new item
func CreatePerson(w http.ResponseWriter, r *http.Request) {
    params := mux.Vars(r)
    var person Person
    _ = json.NewDecoder(r.Body).Decode(&person)
    person.ID = params["id"]
    people = append(people, person)
    json.NewEncoder(w).Encode(people)
}

// Delete an item
func DeletePerson(w http.ResponseWriter, r *http.Request) {
    params := mux.Vars(r)
    for index, item := range people {
        if item.ID == params["id"] {
            people = append(people[:index], people[index+1:]...)
            break
        }
        json.NewEncoder(w).Encode(people)
    }
}

// main function to boot up everything
func main() {
    people = append(people, Person{ID: "1", Firstname: "Son", Lastname: "Pham", Address: &Model.Address{City: "City X", State: "State X"}})
    people = append(people, Person{ID: "2", Firstname: "Hoang", Lastname: "Nguyen", Address: &Model.Address{City: "City Z", State: "State Y"}})

    // InitPerson()

    router := mux.NewRouter()
    router.HandleFunc("/people", GetPeople).Methods("GET")
    router.HandleFunc("/people/{id}", GetPerson).Methods("GET")
    router.HandleFunc("/people/{id}", CreatePerson).Methods("POST")
    router.HandleFunc("/people/{id}", DeletePerson).Methods("DELETE")
    log.Fatal(http.ListenAndServe(":80", router))
}
