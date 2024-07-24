import { Row } from "react-bootstrap";
import { useToken } from "../AuthProvider/useToken";

interface Props {
    children: JSX.Element;
}

export function PrivateRoute(children: Props) {

    const token = useToken();

    if (!token) {
        return <Row className="justify-content-center display-2 mt-5">Você não tem acesso!</Row>
    }
    return ({ ...children })
}