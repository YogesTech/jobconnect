import { Link, Route, Routes } from "react-router-dom";
import { useEffect, useState } from "react";
import { getJobs, Job, logout } from "./api";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import JobDetailsPage from "./pages/JobDetailsPage";
import ApplyPage from "./pages/ApplyPage";
import MyApplicationsPage from "./pages/MyApplicationsPage";
import EmployerDashboardPage from "./pages/EmployerDashboardPage";
import EmployerApplicationsPage from "./pages/EmployerApplicationsPage";
import EditJobPage from "./pages/EditJobPage";
import ProtectedRoute from "./auth/ProtectedRoute";
function Home() {
  const [jobs, setJobs] = useState<Job[]>([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");
  const loadJobs = async (keyword = "") => {
    setIsLoading(true); setErrorMessage("");
    try { setJobs(await getJobs(keyword)); } catch { setErrorMessage("The backend may be waking up on Render. Please try again shortly."); }
    finally { setIsLoading(false); }
  };
  useEffect(() => { loadJobs(); }, []);
  return <main className="mx-auto max-w-6xl px-6 pb-20">
    <section className="-mx-6 bg-gradient-to-br from-blue-700 via-indigo-700 to-slate-900 px-6 py-24 text-white md:rounded-b-[3rem]">
    <p className="font-bold uppercase tracking-widest text-blue-200">JobConnect · Your next chapter</p>
    <h1 className="mt-4 max-w-3xl text-5xl font-black leading-tight md:text-7xl">Find work that fits your life.</h1>
    <p className="mt-5 max-w-2xl text-lg text-slate-200">Explore opportunities without signing in, then apply when you find the right fit.</p>
    <form onSubmit={(event) => { event.preventDefault(); loadJobs(searchKeyword); }} className="mt-10 flex max-w-3xl gap-3 rounded-2xl bg-white p-2 shadow-2xl"><input value={searchKeyword} onChange={(event) => setSearchKeyword(event.target.value)} className="flex-1 rounded-xl px-4 py-3 text-slate-900 outline-none" placeholder="Search by title or location"/><button className="button-primary">Search jobs</button></form>
    </section>
    <section className="grid gap-4 py-10 sm:grid-cols-3"><div className="surface p-5"><p className="text-3xl font-black text-blue-600">10k+</p><p className="mt-1 text-slate-500">Career opportunities</p></div><div className="surface p-5"><p className="text-3xl font-black text-blue-600">2.4k</p><p className="mt-1 text-slate-500">Growing companies</p></div><div className="surface p-5"><p className="text-3xl font-black text-blue-600">100%</p><p className="mt-1 text-slate-500">Free to explore</p></div></section>
    <h2 className="text-3xl font-bold">Latest opportunities</h2>
    {isLoading && <p className="mt-8 text-slate-500">Loading opportunities…</p>}
    {errorMessage && <p className="mt-8 text-red-600">{errorMessage}</p>}
    {!isLoading && !errorMessage && jobs.length === 0 && <p className="mt-8 text-slate-500">No jobs found.</p>}
    <div className="mt-8 grid gap-4 md:grid-cols-2">{jobs.map((job) => <Link to={`/jobs/${job.id}`} className="surface p-5 transition hover:-translate-y-1" key={job.id}><h2 className="text-xl font-bold">{job.title}</h2><p className="text-blue-600">{job.companyName}</p><p className="mt-3 text-slate-500">{job.location} · {job.salary}</p><p className="mt-3 line-clamp-2 text-sm text-slate-600">{job.description}</p></Link>)}</div>
  </main>;
}
export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(() => Boolean(localStorage.getItem("jobconnect_access_token")));
  const [userRole, setUserRole] = useState(() => localStorage.getItem("jobconnect_role"));
  useEffect(() => { const syncAuthentication = () => { setIsAuthenticated(Boolean(localStorage.getItem("jobconnect_access_token"))); setUserRole(localStorage.getItem("jobconnect_role")); }; window.addEventListener("auth-changed", syncAuthentication); return () => window.removeEventListener("auth-changed", syncAuthentication); }, []);
  const handleLogout = async () => { await logout(); setIsAuthenticated(false); };
  return (
    <div className="min-h-screen">
      <header className="border-b bg-white">
        <nav className="mx-auto flex max-w-6xl justify-between px-6 py-5">
          <Link className="text-xl font-bold" to="/">
            JobConnect
          </Link>
          <div className="flex gap-4">
            {userRole !== "EMPLOYER" && <><Link to="/">Jobs</Link><Link to="/applications">Applications</Link></>}
            {userRole === "EMPLOYER" && <><Link to="/employer">Employer Dashboard</Link><Link to="/employer/applications">Review Applications</Link></>}
            {!isAuthenticated && <Link to="/employer">Employer</Link>}
            {isAuthenticated ? <button onClick={handleLogout} className="font-semibold text-red-600">Logout</button> : <><Link to="/register">Register</Link><Link to="/login">Login</Link></>}
          </div>
        </nav>
      </header>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/jobs/:jobId" element={<JobDetailsPage />} />
        <Route element={<ProtectedRoute />}>
          <Route path="/jobs/:jobId/apply" element={<ApplyPage />} />
          <Route path="/applications" element={<MyApplicationsPage />} />
        </Route>
        <Route element={<ProtectedRoute requiredRole="EMPLOYER" />}>
          <Route path="/employer" element={<EmployerDashboardPage />} />
          <Route path="/employer/jobs/:jobId/edit" element={<EditJobPage />} />
          <Route
            path="/employer/applications"
            element={<EmployerApplicationsPage />}
          />
        </Route>
      </Routes>
    </div>
  );
}
