import { Navigate, Outlet, useLocation } from "react-router-dom";
export default function ProtectedRoute({
  requiredRole,
}: {
  requiredRole?: string;
}) {
  const location = useLocation();
  const token = localStorage.getItem("jobconnect_access_token");
  const role = localStorage.getItem("jobconnect_role");
  if (!token)
    return <Navigate to="/login" replace state={{ from: location.pathname }} />;
  if (requiredRole && role !== requiredRole) return <Navigate to="/" replace />;
  return <Outlet />;
}
