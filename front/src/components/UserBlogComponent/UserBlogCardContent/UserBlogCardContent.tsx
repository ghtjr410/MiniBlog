type UserBlogCardContentProps = {
    content: string;
    onClick: () => void;
};

const UserBLogCardContent = ({ content, onClick }: UserBlogCardContentProps) => {
    return <p className="overflow-hidden line-clamp-3"
    onClick={onClick}
    dangerouslySetInnerHTML={{ __html: content}}
    >
    </p>;
}
export default UserBLogCardContent;

// dangerouslySetInnerHTML={{ __html: content}}