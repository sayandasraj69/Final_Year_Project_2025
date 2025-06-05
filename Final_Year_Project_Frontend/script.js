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
    populateSpecializations(allDoctors);
  })
  .catch(error => console.error("Error fetching doctors:", error));

// Populate specialization dropdown from doctors' data
function populateSpecializations(doctors) {
  const specs = new Set();
  doctors.forEach(doc => {
    (doc.specializations || []).forEach(spec => specs.add(spec));
  });
  specializationSelect.innerHTML = '<option value="">Select Specialization</option>';
  specs.forEach(spec => {
    const option = document.createElement("option");
    option.value = spec;
    option.textContent = spec;
    specializationSelect.appendChild(option);
  });
}

// Filter doctors by specialization and name
function filterDoctors() {
  const selectedSpec = specializationSelect.value;
  const searchTerm = searchBar.value.trim().toLowerCase();
  let filtered = allDoctors;

  if (selectedSpec) {
    filtered = filtered.filter(doc =>
      (doc.specializations || []).includes(selectedSpec)
    );
  }
  if (searchTerm) {
    filtered = filtered.filter(doc =>
      (doc.doctorName || "").toLowerCase().includes(searchTerm)
    );
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