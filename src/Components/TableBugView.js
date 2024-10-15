import { Table, Button } from 'reactstrap';
import { useEffect, useState } from 'react';

function TableBugView() {
    const [isLoading, setIsLoading] = useState(true);
    const [bugs, setBugs] = useState([]);


    const fetchBugs = async () => {
        try {
            const response = await fetch("/api/v1/bugs");
            if (!response.ok) {
                throw new Error('Error fetching data');
            }
            const body = await response.json();
            setBugs(body);
            setIsLoading(false);
        } catch (error) {
            console.error('fetch error', error);
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchBugs();
    }, []);

    const handleDelete = async (id) => {
        try {
            const response = await fetch(`/api/v1/bugs/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({id}) // Ensure the body is provided, even if empty

            });
            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Error deleting bug: ${errorMessage}`);
            }
            fetchBugs(); // Refetch bugs after deletion
        } catch (error) {
            console.error('Delete error:', error.message);
        }
    };
    

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return ( 
        <div className="table-container">
            <Table hover>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Priority</th>
                        <th>Status</th>
                        <th>Created</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {bugs.map((bug, index) => (
                        <tr key={bug.id}>
                            <th scope="row">{index + 1}</th>
                            <td>{bug.title}</td>
                            <td>{bug.description}</td>
                            <td>{bug.priority}</td>
                            <td>{bug.status}</td>
                            <td>{new Date(bug.created).toLocaleDateString()}</td>
                            <td>
                                <Button color="danger" onClick={() => handleDelete(bug.id)}>
                                    Delete
                                </Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </div>
    );
}

export default TableBugView;
