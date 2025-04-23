document.getElementById("auteurForm").addEventListener("submit", function (e) {
    e.preventDefault();
  
    const name = document.getElementById("name").value;
    const birthYear = document.getElementById("birthYear").value;
    const nationality = document.getElementById("nationality").value;
  
    fetch("http://localhost:8080/client1/addAuteur", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: `name=${encodeURIComponent(name)}&birth_year=${birthYear}&nationality=${encodeURIComponent(nationality)}`
    })
    .then(response => {
      if (response.ok) {
        window.location.href = "../html/addBook.html"; // Redirection vers page d'ajout du livre
      } else {
        alert("Erreur lors de l'ajout de l'auteur");
      }
    })
    .catch(err => {
      console.error("Erreur :", err);
      alert("Erreur réseau");
    });
  });
  