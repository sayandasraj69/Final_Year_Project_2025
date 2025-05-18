document.addEventListener("DOMContentLoaded", () => {
  const specializationDropdown = document.getElementById("specialization-dropdown");
  const doctorsContainer = document.getElementById("doctors-container");
  const selectedDoctorDiv = document.getElementById("selected-doctor");

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

    // Clear previous doctor cards
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
          console.log("Doctors data:", data); // Log the fetched data
          // Check if the data is an array and has elements
          if (!Array.isArray(data) || data.length === 0) {
            doctorsContainer.innerHTML = "<p>No doctors found for this specialization.</p>";
            return;
          }

          data.forEach(doctor => {
            const doctorCard = document.createElement("div");
            doctorCard.className = "doctor-card";

            doctorCard.innerHTML = `
              <h3>${doctor[1] || "unnamed doctor"}</h3>
              <p>Specialization: ${doctor[0] || "unknown specialization"}</p>
              <button class="select-doctor">Select</button>
            `;
            console.log("doctor name:", doctor.docName);

            doctorsContainer.appendChild(doctorCard);
          });
        })
        .catch(error => {
          doctorsContainer.innerHTML = "<p>Error fetching doctors</p>";
          console.error("Error fetching doctors:", error);
        });
    } else {
      doctorsContainer.innerHTML = "<p>Please select a specialization.</p>";
    }

  });
})
