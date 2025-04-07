document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('id'); // Récupère l'ID du livre depuis l'URL
  
    if (bookId) {
      // Requête GET pour récupérer les détails du livre
      fetch(`http://localhost:8080/getLivre?id=${bookId}`)
        .then(response => response.json())
        .then(data => {
          // Si le livre est trouvé, afficher ses détails
          if (data) {
            document.getElementById('bookDetails').innerHTML = `
              <h2 class="text-xl font-semibold mb-2">${data.title}</h2>
              <p><strong>Auteur:</strong> ${data.auteur.name}</p>
              <p><strong>Année de publication:</strong> ${data.publicationYear}</p>
              <p><strong>Genre:</strong> ${data.genre}</p>
            `;
          } else {
            document.getElementById('bookDetails').innerHTML = '<p>Livre non trouvé.</p>';
          }
        })
        .catch(error => {
          console.error('Erreur:', error);
          document.getElementById('bookDetails').innerHTML = '<p>Une erreur est survenue lors de la récupération des informations du livre.</p>';
        });
    } else {
      document.getElementById('bookDetails').innerHTML = '<p>ID du livre manquant dans l\'URL.</p>';
    }
  });
          