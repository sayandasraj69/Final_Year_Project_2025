let allDoctors = null;

// Get doctorId from URL
const doctorId = new URLSearchParams(window.location.search).get("docId");

// Main function to fetch and display all doctor details
function showDoctorDetails() {
    fetch(`http://localhost:8080/doctor/${doctorId}/details`)
        .then(response => response.json())
        .then(doctor => {
            if (!doctor) {
                document.querySelector(".doctor-details").innerHTML = "<p>Doctor not found.</p>";
                return;
            }


            // Doctor Info
            document.getElementById("doctor-name").textContent = doctor.doctorName || "Unknown Name";

            // Degrees
            let degreeText = (doctor.degree && doctor.degree.length > 0)
                ? "Degrees: " + doctor.degree.join(", ")
                : (doctor.qualification && doctor.qualification.length > 0)
                    ? "Degrees: " + doctor.qualification.join(", ")
                    : (doctor.qualifications && doctor.qualifications.length > 0)
                        ? "Degrees: " + doctor.qualifications.join(", ")
                        : "No degrees listed";

            // Specializations
            let specializationText = doctor.specialization && doctor.specialization.length > 0
                ? "Specializations: " + doctor.specialization.join(", ")
                : "No specialization listed";

            // Show degrees and specializations together
            document.getElementById("doctor-specialization").innerHTML = `${degreeText}<br>${specializationText}`;

            // Schedule
            const scheduleContainer = document.querySelector(".doctor-schedule");
            scheduleContainer.innerHTML = "<h2>Doctor's Schedule</h2>";

            if (!doctor.schedule || doctor.schedule.length === 0) {
                scheduleContainer.innerHTML += "<p>No schedule available.</p>";
            } else {
                doctor.schedule.forEach(day => {
                    let dayElement = document.createElement("div");
                    dayElement.classList.add("schedule-day");
                    dayElement.innerHTML = `<h3>${day.scheduleDay || day.weekday || "Day"}</h3>`;

                    if (day.timings && day.timings.length > 0) {
                        day.timings.forEach(slot => {
                            let slotElement = document.createElement("div");
                            slotElement.classList.add("schedule-slot");
                            slotElement.innerHTML = `
                                <p>
                                    <strong>Time:</strong> ${slot.timingRange || slot.timeRange || ""} <br>
                                    <strong>City:</strong> ${slot.city || ""} <br>
                                    <strong>Center:</strong> ${slot.center || ""}
                                </p>
                            `;
                            dayElement.appendChild(slotElement);
                        });
                    } else {
                        dayElement.innerHTML += "<p>No timings available.</p>";
                    }

                    scheduleContainer.appendChild(dayElement);
                });
            }
        })
        .catch(error => {
            console.error("Error fetching doctor details:", error);
            document.querySelector(".doctor-details").innerHTML = "<p>Error fetching doctor details.</p>";
        });
}

// Call the function on page load
showDoctorDetails();

// show similar doctors search by similar specialization
function showSimilarDoctors() {
    if (!allDoctors) {
        console.error("All doctors data is not available.");
        return;
    }

    const doctorName = document.getElementById("doctor-name").textContent;
    const doctorSpecialization = document.getElementById("doctor-specialization").textContent;

    const similarDoctors = allDoctors.filter(doc => {
        return doc.doctorName !== doctorName && doc.specialization && doc.specialization.some(spec => doctorSpecialization.includes(spec));
    });

    const similarDoctorsList = document.getElementById("similar-doctors-list");
    similarDoctorsList.innerHTML = ""; // Clear previous results

    if (similarDoctors.length === 0) {
        similarDoctorsList.innerHTML = "<p>No similar doctors found.</p>";
        return;
    }

    similarDoctors.forEach(doctor => {
        const doctorDiv = document.createElement("div");
        doctorDiv.className = "doctor";
        doctorDiv.innerHTML = `
            <h3>${doctor.doctorName || "Unknown Name"}</h3>
            <div>Specializations: ${doctor.specialization ? doctor.specialization.join(", ") : "No specialization listed"}</div>
        `;

        doctorDiv.addEventListener("click", () => {
            window.location.href = `docDetail.html?docId=${doctor.docId}`;
        });

        similarDoctorsList.appendChild(doctorDiv);
    });
}
