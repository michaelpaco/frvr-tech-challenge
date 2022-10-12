const NATIVE_LOG_MESSAGE_ID = "native_logs__mesage";
const LOCAL_NOTIFICATION_DISPLAY_TIME_ID = "local_notifications__display-time";
const LOCAL_NOTIFICATION_ID = "local_notifications__id";
const LOCAL_NOTIFICATION_TITLE_ID = "local_notifications__title";
const LOCAL_NOTIFICATION_MESSAGE_ID = "local_notifications__message";
const ERROR_MESSAGE_CONTAINER_ID = "error";
const ERROR_MESSAGE_TEXT_ID = "error__message";

function onLoadApp() {
  // set default value for timepicker
  document.getElementById(LOCAL_NOTIFICATION_DISPLAY_TIME_ID).value = new Date()
    .toISOString()
    .slice(0, -8);

  // show selected app by user
  const param = new URLSearchParams(window.location.search);

  if (!param.get("app")) return showErrorMessage("App identifier expected");

  const appContainer = document.getElementById(param.get("app"));

  if (!appContainer)
    return showErrorMessage("The specified app does not exist.");

  appContainer.style.display = "flex";
}

function showErrorMessage(message) {
  document.getElementById(ERROR_MESSAGE_CONTAINER_ID).style.display = "flex";
  document.getElementById(ERROR_MESSAGE_TEXT_ID).innerText = message;
}

function sendMessageToNative() {
  const message = document.getElementById(NATIVE_LOG_MESSAGE_ID).value;

  if (!message.length) return console.error("Empty message");

  window.Android?.showMessageFromWebView(message);
}

function registerNotification() {
  const displayTime = document.getElementById(
    LOCAL_NOTIFICATION_DISPLAY_TIME_ID
  ).value;
  const id = document.getElementById(LOCAL_NOTIFICATION_ID).value;
  const title = document.getElementById(LOCAL_NOTIFICATION_TITLE_ID).value;
  const message = document.getElementById(LOCAL_NOTIFICATION_MESSAGE_ID).value;

  if (!displayTime || !id || !title || !message)
    return console.error("All values must be filled");

  window.Android?.registerNotification(displayTime, Number(id), title, message);
}

function getAllNotifications() {
  const notifications = window.Android?.getAllNotifications();

  const data = JSON.parse(notifications);
  const tableData = data
    .map(
      (value) => `<tr>
                      <td>${value.displayTime}</td>
                      <td>${value.id}</td>
                      <td>${value.title}</td>
                      <td>${value.message}</td>
                  </tr>`
    )
    .join("");

  const tableBody = document.querySelector("#local_notifications__table");
  tableBody.innerHTML = tableData;
}
