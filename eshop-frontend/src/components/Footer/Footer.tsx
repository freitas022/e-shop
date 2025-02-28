import { BsLinkedin } from "react-icons/bs";

function Footer() {
    return (
        <footer className="py-3 bg-dark text-white">
            <div className="d-flex align-items-center justify-content-center gap-2">
                <BsLinkedin size={25} style={{ color: "goldenrod" }} />
                <span className="fs-5">/freitas-dev</span>
            </div>
            <p className="text-center pt-2">
                E-shop &copy; 2025
            </p>
        </footer>
    );
}

export default Footer;