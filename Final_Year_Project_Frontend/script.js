const allDoctors = [];
const doctorList = document.getElementById("doctors-list");
const specializationSelect = document.getElementById("specialization-dropdown");
const searchBar = document.getElementById("searchBar");

// Fetch all doctors
fetch("http://localhost:8080/doctor/all")
  .then(response => response.json())
  .then(data => {
    allDoctors.push(...data);
    displayDoctors(allDoctors);
  })
  .catch(error => console.error("Error fetching doctors:", error));

// Fetch all specializations for the dropdown
fetch("http://localhost:8080/doctor/specializations")
  .then(response => response.json())
  .then(data => {
    // Do NOT reset innerHTML, so the placeholder remains
    data.forEach(specialization => {
      // If backend returns [id, name]
      const specName = Array.isArray(specialization) ? specialization[1] : (specialization.name || specialization);
      const option = document.createElement("option");
      option.value = specName;
      option.textContent = specName;
      specializationSelect.appendChild(option);
    });
  })
  .catch(error => console.error("Error fetching specializations:", error));

// Filter doctors by specialization and name
function filterDoctors() {
  // Get selected specializations, ignoring empty values
  const selectedSpecs = Array.from(specializationSelect.selectedOptions)
    .map(opt => opt.value)
    .filter(val => val);

  const searchTerm = searchBar.value.trim().toLowerCase();

  let filtered = allDoctors;

  // If only specialization(s) selected
  if (selectedSpecs.length > 0 && !searchTerm) {
    filtered = allDoctors.filter(doc =>
      selectedSpecs.every(spec =>
        (doc.specializations || []).includes(spec)
      )
    );
  }
  // If only name entered
  else if (searchTerm && selectedSpecs.length === 0) {
    filtered = allDoctors.filter(doc =>
      (doc.doctorName || "").toLowerCase().includes(searchTerm)
    );
  }
  // If both are used
  else if (searchTerm && selectedSpecs.length > 0) {
    filtered = allDoctors.filter(doc =>
      (doc.doctorName || "").toLowerCase().includes(searchTerm) &&
      selectedSpecs.every(spec =>
        (doc.specializations || []).includes(spec)
      )
    );
  }
  // If neither, show all
  else {
    filtered = allDoctors;
  }

  displayDoctors(filtered);
}

// Event listeners
specializationSelect.addEventListener("change", filterDoctors);
searchBar.addEventListener("input", filterDoctors);

// Display doctors
function displayDoctors(doctors) {
  if (!doctorList) return;
  doctorList.innerHTML = "";
  if (doctors.length === 0) {
    doctorList.innerHTML = "<p>No doctors found.</p>";
    return;
  }
  doctors.forEach(doctor => {
    const doctorName = doctor.doctorName || "Unknown Name";
    const specializations = doctor.specializations || [];
    const specializationText = specializations.length > 0
      ? specializations.join(", ")
      : "No specialization listed";
    const doctorDiv = document.createElement("div");
    doctorDiv.className = "doctor";
    doctorDiv.innerHTML = `
      <h3>${doctorName}</h3>
      <div>Specializations: ${specializationText}</div>
    `;
    doctorDiv.addEventListener("click", () => {
      window.location.href = `docDetail.html?docId=${doctor.docId}`;
    });
    doctorList.appendChild(doctorDiv);
  });
}