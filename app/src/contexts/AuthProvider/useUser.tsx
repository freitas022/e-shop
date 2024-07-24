import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from "react";
import { useToken } from "./useToken";

interface User {
    id: number;
    name: string;
    email: string;
}

export const useUser = (): User | null => {
    const [token] = useToken();

    const getPayloadFromToken = (token: string): User | null => {
        const encodedPayload = token;
        const decodedPayload = jwtDecode(encodedPayload);
        return decodedPayload as User;
    }

    const [user, setUser] = useState<User | null>(() => {
        if (!token) return null;
        return getPayloadFromToken(token);
    });

    useEffect(() => {
        if (!token) {
            setUser(null);
        } else {
            axios.get(`http://localhost:8080/users/me`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }).then(response => {
                setUser(response.data)
            }).catch(error => {
                setUser(null);
                console.error(error);
            })
        }
    }, [token]);

    return user;
}