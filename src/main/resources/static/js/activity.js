// Global Activity Logger for TGMS (frontend-only)
(function(){
  const LOG_KEY = 'tgms_activity_logs';
  const CUR_USER_KEY = 'tgms_current_user';

  function loadLogs(){ try { return JSON.parse(localStorage.getItem(LOG_KEY)||'[]'); } catch { return []; } }
  function saveLogs(list){ try { localStorage.setItem(LOG_KEY, JSON.stringify(list)); } catch(e){ console.error(e); } }
  function getCurrentUser(){
    try { return JSON.parse(localStorage.getItem(CUR_USER_KEY)||'null'); } catch { return null; }
  }

  function logActivity(action, details, email){
    const logs = loadLogs();
    const userEmail = email || (getCurrentUser() && getCurrentUser().email) || 'guest';
    logs.push({ time: new Date().toISOString(), user: userEmail, action, details });
    saveLogs(logs);
  }

  function logPageView(name){
    logActivity('PAGE_VIEW', name || document.title);
  }

  // Expose
  window.tgmsLogs = { loadLogs, saveLogs };
  window.logActivity = logActivity;
  window.logPageView = logPageView;
})();
