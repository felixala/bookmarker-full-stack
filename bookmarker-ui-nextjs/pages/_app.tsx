import '@/styles/globals.css'
import type { AppProps } from 'next/app'
import 'bootstrap/dist/css/bootstrap.min.css'
import NavBar from "@/components/NavBar";

export default function App({ Component, pageProps }: AppProps) {
  return (
      <>
        <NavBar/>
        <main>
          <Component {...pageProps} />
        </main>
      </>

      )
}
