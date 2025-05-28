let allDoctors = null;
fetch("http://localhost:8080/all")
    .then(response => response.json())
    .then(data => {
        allDoctors = data;
        console.log(allDoctors);
        findDoctorById();
    })
    .catch(error => console.error("Error fetching doctors:", error));

//   find the doctor by id
function findDoctorById() {
    let detail = document.querySelector(".doctor-details");
    let doctorId = new URLSearchParams(window.location.search).get("docId");
    let doctor = allDoctors.find(doctor => doctor.docId == doctorId);

    if (!doctor) {
        detail.innerHTML = "<p>Doctor not found.</p>";
        return;
    }
    //   doctor details
    detail.innerHTML = `
    <div class="doctor-info">
        <h2>${doctor.doctorName}</h2>
        <p>Specializations: ${doctor.specializations.join(", ")}</p>
    </div>
`;
}
// showing doctor schedule
let doctorId = new URLSearchParams(window.location.search).get("docId");
//   show doctor schedule
function showDoctorSchedule() {
    let scheduleContainer = document.querySelector(".doctor-schedule");
    scheduleContainer.innerHTML = ""; // Clear previous schedule

    // Fetch the doctor's schedule
    fetch(`http://localhost:8080/${doctorId}/details`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (!data || !data.schedule || data.schedule.length === 0) {
                scheduleContainer.innerHTML = "<p>No schedule available.</p>";
                return;
            }

            // Display the schedule
            data.schedule.forEach(day => {
                let dayElement = document.createElement("div");
                dayElement.classList.add("schedule-day");
                dayElement.innerHTML = `
                    <input type="date" value="${getDateOfWeekday(day.weekday)}">
                    <label><h2>${day.weekday}</h2></label>

                  `;

                day.timings.forEach(slot => {
                    let slotElement = document.createElement("div");
                    slotElement.classList.add("schedule-slot");
                    slotElement.innerHTML = `
                        <p><strong>Time:</strong> ${slot.timeRange}</p>
                   
                    `;
                    dayElement.appendChild(slotElement);
                });

                scheduleContainer.appendChild(dayElement);
            });
        })
        .catch(error => {
            console.error("Error fetching schedule:", error);
            scheduleContainer.innerHTML = "<p>Error fetching schedule.</p>";
        });
}
//  show doctor schedule
showDoctorSchedule();

function getDateOfWeekday(weekday) {
    const weekdayMap = {
        "Sunday": 0, "Monday": 1, "Tuesday": 2, "Wednesday": 3,
        "Thursday": 4, "Friday": 5, "Saturday": 6
    };
    const today = new Date();
    const todayDay = today.getDay();
    const targetDay = weekdayMap[weekday];
    const diff = (targetDay + 7 - todayDay) % 7;
    const date = new Date(today);
    date.setDate(today.getDate() + diff);
    return date.toISOString().split('T')[0];
}