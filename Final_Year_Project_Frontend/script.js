let allDoctors = []; // Store all fetched doctors for searching

document.addEventListener("DOMContentLoaded", () => {
  const specializationDropdown = document.getElementById("specialization-dropdown");
  const doctorsContainer = document.getElementById("doctors-container");
  const selectedDoctorDiv = document.getElementById("selected-doctor");
  const searchInput = document.getElementById("search-doctor");

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
    })
    .catch(error => console.error("Error fetching specializations:", error));

  // Listen for dropdown changes
  specializationDropdown.addEventListener("change", () => {
    const selectedSpecialization = specializationDropdown.value;
    doctorsContainer.innerHTML = "";
    selectedDoctorDiv.innerHTML = ""; // Clear selected doctor info

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
          allDoctors = data; // Save for search
          renderDoctors(data);
        })
        .catch(error => {
          doctorsContainer.innerHTML = "<p>Error fetching doctors</p>";
          console.error("Error fetching doctors:", error);
        });
    } else {
      doctorsContainer.innerHTML = "<p>Please select a specialization.</p>";
    }
  });

  // Search handler
  searchInput.addEventListener("input", handleDoctorSearch);

  function handleDoctorSearch() {
    const query = searchInput.value.trim().toLowerCase();
    const filtered = allDoctors.filter(doctor =>
      doctor[1] && doctor[1].toLowerCase().includes(query)
    );
    renderDoctors(filtered);
  }

  function renderDoctors(doctors) {
    doctorsContainer.innerHTML = "";
    if (!Array.isArray(doctors) || doctors.length === 0) {
      doctorsContainer.innerHTML = "<p>No doctors found.</p>";
      return;
    }
    doctors.forEach(doctor => {
      const doctorCard = document.createElement("div");
      doctorCard.className = "doctor-card";
      doctorCard.innerHTML = `
        <h3>${doctor[1] || "unnamed doctor"}</h3>
        <p>Specialization: ${doctor[0] || "unknown specialization"}</p>
        <button class="select-doctor">Select</button>
      `;
      doctorCard.querySelector(".select-doctor").addEventListener("click", () => {
        selectedDoctorDiv.innerHTML = `
          <h2>${doctor[1] || "unnamed doctor"}</h2>
          <p>Specialization: ${doctor[0] || "unknown specialization"}</p>
        `;
      });
      doctorsContainer.appendChild(doctorCard);
    });
  }
});

