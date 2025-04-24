document.addEventListener("DOMContentLoaded", () => {
    const authorSelect = document.getElementById("authorSelect");
  
    // Chargement des auteurs dans le select
    fetch("http://localhost:8080/client1/getAuteurs")
      .then(response => response.json())
      .then(data => {
        data.forEach(author => {
          const option = document.createElement("option");
          option.value = author.id;
          option.textContent = author.name;
          authorSelect.appendChild(option);
        });
      })
      .catch(err => {
        console.error("Erreur lors du chargement des auteurs :", err);
        alert("Impossible de charger les auteurs.");
      });
  
    // Soumission du formulaire
    document.getElementById("bookForm").addEventListener("submit", e => {
      e.preventDefault();
  
      const title = document.getElementById("title").value;
      const genre = document.getElementById("genre").value;
      const publicationYear = document.getElementById("publicationYear").value;
      const authorId = authorSelect.value;
  
      fetch("http://localhost:8080/client1/addLivre", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: `title=${encodeURIComponent(title)}&genre=${encodeURIComponent(genre)}&publication_year=${publicationYear}&author_id=${authorId}`
      })
      .then(response => {
        if (response.ok) {
          alert("Livre ajouté avec succès !");
          window.location.href = "AllBooks.html";
        } else {
          alert("Erreur lors de l'ajout du livre.");
        }
      })
      .catch(err => {
        console.error("Erreur :", err);
        alert("Erreur réseau");
      });
    });
  });
  