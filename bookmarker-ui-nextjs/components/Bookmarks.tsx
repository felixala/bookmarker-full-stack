import React from 'react';
import {BookmarksResponse} from "@/services/models";
import Bookmark from "@/components/bookmark";
import Pagination from "@/components/pagination";

interface BookmarksProps{
bookmarks: BookmarksResponse,
    query?:string
}

const Bookmarks: React.FC<BookmarksProps> = ({bookmarks, query}) => (
    <div>
        <Pagination bookmarks = {bookmarks} query={query}/>
        {bookmarks.data.map(bookmark => <Bookmark key={bookmark.id} bookmark={bookmark}/> )}
    </div>
);

export default Bookmarks;