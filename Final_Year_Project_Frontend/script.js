document.addEventListener("DOMContentLoaded", () => {
    const specializationDropdown = document.getElementById("specialization-dropdown");
    const doctorsContainer = document.getElementById("doctors-container");
  
    // Fetch specializations from the backend
    fetch("http://localhost:8080/specialization_names")
      .then(response => response.json())
      .then(data => {
        data.forEach(specialization => {
          const option = document.createElement("option");
          option.value = specialization;
          option.textContent = specialization;
          specializationDropdown.appendChild(option);
          });
        });
      })
      .catch(error => console.error("Error fetching specializations:", error));
  
    // Listen for dropdown changes
  specializationDropdown.addEventListener("change", () => {
    const selectedSpecialization = specializationDropdown.value;

    // Clear previous doctor cards
    doctorsContainer.innerHTML = "";

    if (selectedSpecialization) {
      // Fetch doctors based on the selected specialization
      fetch(`http://localhost:8080/minor/${selectedSpecialization}`)
        .then(response => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          return response.json();
        })
        .then(data => {
          if (!Array.isArray(data) || data.length === 0) {
            doctorsContainer.innerHTML = "<p>No doctors found for this specialization.</p>";
            return;
          }

          data.forEach(doctor => {
            const doctorCard = document.createElement("div");
            doctorCard.className = "doctor-card";

            doctorCard.innerHTML = `
              <h3>${doctor.name}</h3>
              <p>${doctor.specialization}</p>
            `;

            doctorsContainer.appendChild(doctorCard);
        });
      })
      .catch(error => console.error("Error fetching doctors:", error));
  });