const allDoctors = [];

const doctorList = document.getElementById("doctors-list");

fetch("http://localhost:8080/doctor/all")
  .then(response => response.json())
  .then(data => {
    allDoctors.push(...data);
    displayDoctors(allDoctors);
    console.log(allDoctors);
  })
  .catch(error => console.error("Error fetching doctors:", error));

// Function to display doctors
function displayDoctors(doctors) {
  doctorList.innerHTML = ""; // Clear previous results
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

    // Create a container for each doctor
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