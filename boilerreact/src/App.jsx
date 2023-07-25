
import { BrowserRouter, Routes, Route, useNavigate, useLocation } from 'react-router-dom'
import './App.css';
import Home from './pages/Home';
import About from './pages/About';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import Layout from './layout/Layout';

function App() {
  const navigate = useNavigate();

  return (
    <Layout>
      <Routes>
        <Route path='/' element={<Home/>} />
        <Route path='/about' element={<About/>} />
        <Route path='/login' element={<Login/>} />
        <Route path='/signUp' element={<SignUp/>} />
      </Routes>
    </Layout>
  );
}

export default App;
