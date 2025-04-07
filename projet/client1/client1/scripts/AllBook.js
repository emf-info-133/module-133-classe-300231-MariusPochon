// document.addEventListener('DOMContentLoaded', () => {
//     fetch('http://localhost:8080/getLivres') // Adapte l'URL si nécessaire
//         .then(response => {
//             if (!response.ok) throw new Error('Erreur de requête');
//             return response.json();
//         })
//         .then(livres => {
//             const tbody = document.getElementById('livresBody');
//             livres.forEach(livre => {
//                 const tr = document.createElement('tr');
//                 tr.innerHTML = `
//                     <td class="px-4 py-2">${livre.title}</td>
//                     <td class="px-4 py-2">${livre.genre}</td>
//                     <td class="px-4 py-2">${livre.publicationYear}</td>
//                     <td class="px-4 py-2">${livre.auteur ? livre.auteur.name : '—'}</td>
//                 `;
//                 tbody.appendChild(tr);
//             });
//         })
//         .catch(error => {
//             console.error('Erreur lors du chargement des livres :', error);
//         });
// });

document.addEventListener("DOMContentLoaded", function() {
    // Requête GET pour récupérer tous les livres
    fetch('http://localhost:8080/getLivres')
      .then(response => response.json())
      .then(data => {
        // Si des livres sont récupérés, les afficher dans la liste
        if (Array.isArray(data) && data.length > 0) {
          const bookListElement = document.getElementById('bookList');
          data.forEach(livre => {
            const li = document.createElement('li');
            li.classList.add('p-2', 'bg-white', 'shadow-sm', 'rounded-md');
            li.innerHTML = `<a href="BookInfo.html?id=${livre.id}" class="text-blue-500 hover:text-blue-700">${livre.title}</a>`;
            bookListElement.appendChild(li);
          });
        } else {
          document.getElementById('bookList').innerHTML = '<li>Aucun livre disponible.</li>';
        }
      })
      .catch(error => {
        console.error('Erreur:', error);
        document.getElementById('bookList').innerHTML = '<li>Une erreur est survenue lors du chargement des livres.</li>';
      });
  });
  
